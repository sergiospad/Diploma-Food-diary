import {Component, inject, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import CategoryNameDTO from '../../../../DTO/entity_dto/recipe-recource/category/category-name.dto';
import CategoryService from '../../../../service/recipe_resource/category.service';
import ProductCreateDTO from '../../../../DTO/entity_dto/product/product-create.dto';
import ProductService from '../../../../service/product.service';
import {NotificationService} from '../../../../security/notification-service';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {MatButton} from '@angular/material/button';
import {httpErrorMessage} from '../../../../util/error.service';

@Component({
  selector: 'app-add-product',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatSelect,
    MatOption,
    MatError,
    MatButton,
  ],
  templateUrl: './add-product.html',
  styleUrl: './add-product.css',
})
export default class AddProductComponent implements OnInit {
  private readonly dialogRef = inject(MatDialogRef<AddProductComponent>);
  private readonly categoryService = inject(CategoryService);
  private readonly productService = inject(ProductService);
  private readonly notificationService = inject(NotificationService);
  public productForm!:FormGroup;
  public fb = inject(FormBuilder);
  public categories: CategoryNameDTO[] = [];

  ngOnInit(): void {
    this.productForm = this.createProductForm();
    this.getCategories();
  }

  createProductForm():FormGroup{
    return this.fb.group({
      title: ['', [Validators.required]],
      description: [''],
      categoryID:[null, Validators.required],
      calories:[null, [Validators.required, Validators.max(1000)]],
      proteins:[null, [Validators.required, Validators.max(100)]],
      fat:[null, [Validators.required, Validators.max(100)]],
      carbs:[null, [Validators.required, Validators.max(100)]],
    });
  }

  getCategories(){
    this.categoryService.getAll().subscribe((res) => {
      this.categories = res ?? [];
    });
  }

  private formPost(){
    const prod = {
      title: this.productForm.value.title,
      description: this.productForm.value.description,
      categoryId: this.productForm.value.categoryID,
      calories: Number.parseFloat(this.productForm.value.calories),
      protein: Number.parseFloat(this.productForm.value.proteins),
      fat: Number.parseFloat(this.productForm.value.fat),
      carbs: Number.parseFloat(this.productForm.value.carbs),
      isPrivate: false,

    } as ProductCreateDTO;
    console.log(prod);
    return prod;
  }

  submit(){
    this.productService.createProduct(this.formPost())
      .subscribe({
        next: res => this.notificationService.showSnackBar("Создан новый продукт"),
        error: (error: unknown)=> this.notificationService.showSnackBar(httpErrorMessage(error, "Ошибка создания продукта")),
        complete: ()=> {
          this.closeDialog()
          globalThis.location.reload();
        }
      });
  }
  closeDialog(){
    this.dialogRef.close();
  }
}
