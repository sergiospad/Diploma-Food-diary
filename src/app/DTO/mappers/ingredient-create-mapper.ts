import IngredientCreateView from '../entity_dto/recipe-recource/ingredient/ingredient-create.view';
import IngredientCreateDTO from '../entity_dto/recipe-recource/ingredient/ingredient-create.dto';
import Mapper from './mapper';
import {Injectable} from '@angular/core';

@Injectable( {providedIn: 'root'})
export default class IngredientCreateMapper implements Mapper<IngredientCreateView, IngredientCreateDTO>{
  map(from: IngredientCreateView): IngredientCreateDTO {
    return {
      productID: from.productID,
      measureUnitID: from.measureUnitID,
      amount: from.amount
    } as IngredientCreateDTO;
  }

}
