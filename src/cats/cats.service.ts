import { HttpException, Injectable, UnauthorizedException } from '@nestjs/common';
import { CatRequestDto } from './dto/cats.request.dto';
import { InjectModel } from '@nestjs/mongoose';
import { Cat } from './cats.schema';
import { Model } from 'mongoose';
import * as bcrypt from 'bcrypt';
import { catLoginDto } from './dto/cats.login.dto';
import { CatsRepository } from './cats.repository';

@Injectable()
export class CatsService {
    constructor(private readonly catsRepositry: CatsRepository) {}

    async signUp(body: CatRequestDto) {
        const { email, name, password } = body;
        //중복확인
        const isCatExist = await this.catsRepositry.existsByEmail(email); //catmodel안에 emial 필드를 검색해서 일치하는지 체크해준다 Promise<boolean> 형식으로 return
        if (isCatExist) {
            throw new UnauthorizedException('해당하는 고양이는 이미 존재합니다');//403에러 일으킴
        }

        //패스워드암호화 //bcrypt 라이브러리로 암호화
        const hashedPassword = await bcrypt.hash(password, 10);
        const cat = await this.catsRepositry.create({
            email,
            name,
            password: hashedPassword
        })
        console.log(cat.readOnlyData);
        return cat.readOnlyData;
    }

    //로그인
    async login(body: catLoginDto) {
        const { email, password } = body;
        const cat = await this.catsRepositry.findCatByEmail( email );
        if (cat) {
            const pwd = await bcrypt.compare(password, cat.password);
            if (pwd) {
                console.log('true');
                return true;
            } else {
                console.log('false');
                return false;
            }
        } else {
            throw new HttpException('아이디 또는 비밀번호가 일치하지 않습니다', 403);
        }
    }
}
