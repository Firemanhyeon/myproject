import { Injectable, UnauthorizedException } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { ExtractJwt, Strategy } from "passport-jwt";
import { Payload } from "./jwt.payload";
import { CatsRepository } from "src/cats/cats.repository";

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
    constructor(private readonly catsRepository : CatsRepository) {
        super({
          jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(), //헤더로부터 추출한다
          secretOrKey: process.env.JWT_SECRET, //시크릿키 , 환경변수로 지정해줘야함.
          ignoreException: false, //
        });
    }
    async validate(payload: Payload) { 
        const cat = await this.catsRepository.findCatByIdWithoutPassword(
            payload.sub,
        );
        if (cat) {
            return cat; //request.user
        } else {
            throw new UnauthorizedException('접근오류');
        }
    }
}