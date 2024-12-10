import { Body, Controller, Delete, Get, HttpException, Param, ParseIntPipe, Patch, Post, Put, UseFilters, UseInterceptors } from '@nestjs/common';
import { CatsService } from './cats.service';
import { HttpExceptionFilter } from 'src/common/exceptions/http-exception.filter';
import { SuccessInterceptor } from 'src/common/interceptors/success.interceptor';
import { CatRequestDto } from './dto/cats.request.dto';
import { catLoginDto } from './dto/cats.login.dto';
import { ApiOperation, ApiResponse } from '@nestjs/swagger';
import { readOnlyCatDto } from './dto/cat.dto';

@Controller('cats')
@UseInterceptors(SuccessInterceptor)
@UseFilters(HttpExceptionFilter)
export class CatsController {
  constructor(private readonly catsService: CatsService) {}

  @ApiOperation({ summary: '현재고양이가져오기' })
  @Get()
  getCurrentCat() {
    return '123';
  }

  @ApiResponse({
    status: 500,
    description:'Server Error'
  })
  @ApiResponse({
    status: 200,
    description: 'Success',
    type: readOnlyCatDto
    })
  @ApiOperation({ summary: '회원가입' })
  @Post()
  async signUp(@Body() body: CatRequestDto) {
    // console.log(body);
    return await this.catsService.signUp(body);
  }
  @ApiOperation({ summary: '로그인' })
  @Post('login')
  async login(@Body() body: catLoginDto) {
    return await this.catsService.login(body);
  }
  @ApiOperation({ summary: '로그아웃' })
  @Post('logout')
  logOut() {
    return 'logout';
  }
  @ApiOperation({ summary: '이미지업로드' })
  @Post('upload/cats')
  uploadCatImg() {
    return 'upload img';
  }
}
