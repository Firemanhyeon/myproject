import { Injectable } from "@nestjs/common";
import { PassportStrategy } from "@nestjs/passport";
import { ExtractJwt, Strategy } from "passport-jwt";

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
    constructor() {
        super({
          jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(), //헤더로부터 추출한다
          secretOrKey: 'secret', //시크릿키 , 환경변수로 지정해줘야함.
          ignoreException: false, //
        });
    }
    // async validate(payload) { }
}