import { HttpException, Injectable } from "@nestjs/common";
import { Cat } from "./cats.schema";
import { Model } from "mongoose";
import { InjectModel } from "@nestjs/mongoose";
import { CatRequestDto } from "./dto/cats.request.dto";


@Injectable()
export class CatsRepository {
  constructor(@InjectModel(Cat.name) private readonly catModel: Model<Cat>) {}

  async existsByEmail(email: string): Promise<boolean> {
    try {
        const result = await this.catModel.exists({ email });
        return result==null ? false : true;
    } catch (error) {
      throw new HttpException('db error', 400);
    }
  }
    
    async create(cat: CatRequestDto): Promise<Cat> {
        return await this.catModel.create(cat);
    }
    //이메일있는지없는지
    async findCatByEmail(email: string): Promise<Cat>{
        const find = await this.catModel.findOne({ email });
        return find ;
    }

}