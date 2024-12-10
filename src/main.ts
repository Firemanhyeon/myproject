import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { HttpExceptionFilter } from './common/exceptions/http-exception.filter';
import { ValidationPipe } from '@nestjs/common';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.useGlobalPipes(new ValidationPipe());//schema.ts 에서 class-validation을 쓰는데 사용하기위한선언
  app.useGlobalFilters(new HttpExceptionFilter());//예외처리 필터선언
  const PORT = process.env.PORT;

   const config = new DocumentBuilder()
     .setTitle('C.I.C')
     .setDescription('CAT')
     .setVersion('1.0')
     .addTag('cats')
     .build();
   const documentFactory = () => SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api', app, documentFactory);
  app.enableCors({
    origin: true,
    credentials:true
  })
  await app.listen(PORT);
}
bootstrap();
