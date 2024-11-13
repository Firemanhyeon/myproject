import { Controller, Delete, Get, HttpException, Param, ParseIntPipe, Patch, Post, Put, UseFilters } from '@nestjs/common';
import { CatsService } from './cats.service';
import { HttpExceptionFilter } from 'src/http-exception.filter';

@Controller('cats')
export class CatsController {
  constructor(private readonly catsService: CatsService) {}

  @Get()
  @UseFilters(HttpExceptionFilter)
  getAllCat() {
    throw new HttpException('api is broken', 401);
    return 'all cat';
  }
  @Get(':id')
  getOneCat(@Param('id',ParseIntPipe) param: number) {
      console.log(param);
      console.log(typeof param)
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