import { Injectable, UnauthorizedException } from '@nestjs/common';
import { CatsRepository } from 'src/cats/cats.repository';
import { LoginRequestDto } from './dto/login.request.dto';
import * as bcrypt from 'bcrypt';
import { JwtService } from '@nestjs/jwt';


@Injectable()
export class AuthService {
    constructor(
        private readonly catRepository: CatsRepository,
        private jwtService: JwtService,//authmodule.ts 에 jwtmodule 안에 있는 공급자 
    ) { }


    async jwtLogIn(data: LoginRequestDto) {
        const { email, password } = data;

        // 해당하는 이메일이 있는지
        const cat = await this.catRepository.findCatByEmail(email);
        if (!cat) {
            throw new UnauthorizedException('이메일과 비밀번호를 확인하세요');
        }

        // 비밀번호 일치확인
        const isPasswordValidated: boolean = await bcrypt.compare(
            password,
            cat.password,
        )
        if (!isPasswordValidated) {
            throw new UnauthorizedException('이메일과 비밀번호를 확인하세요');
        }
        
        const payload = { email: email, sub: cat.id };

        return {
            token: this.jwtService.sign(payload)
        }


    }
}
