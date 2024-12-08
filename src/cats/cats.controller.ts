import { Controller, Delete, Get, HttpException, Param, ParseIntPipe, Patch, Post, Put, UseFilters, UseInterceptors } from '@nestjs/common';
import { CatsService } from './cats.service';
import { HttpExceptionFilter } from 'src/common/exceptions/http-exception.filter';
import { SuccessInterceptor } from 'src/common/interceptors/success.interceptor';

@Controller('cats')
@UseInterceptors(SuccessInterceptor)
@UseFilters(HttpExceptionFilter)
export class CatsController {
  constructor(private readonly catsService: CatsService) {}

  @Get()
  getAllCat() {
    console.log('hello controller');
    // throw new HttpException('api is broken', 401);
    return 'all cat';
  }
  @Get(':id')
  getOneCat(@Param('id', ParseIntPipe) param: number) {
    console.log(param);
    console.log(typeof param);
    return 'one cat';
  }

  @Post()
  createCat() {
    return 'create cat';
  }

  @Put(':id')
  updateCat() {
    return 'update cat';
  }
  @Patch(':id')
  updatePartialCat() {
    return 'update cat';
  }
  @Delete(':id')
  deleteCat() {
    return 'update cat';
  }
}
