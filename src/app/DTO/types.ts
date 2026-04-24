export type UserRole = 'ADMIN' | 'USER' | 'GUEST';

export type SortTypeRecipe = 'NEWER' | 'OLDER' | 'POPULAR';

export type CaloricityType = 'PER_HUNDRED' | 'ALL';

export type NutritionType = 'PRODUCT' | 'RECIPE';

export type ImageType = 'RECIPE' | 'USER' | 'COOKING_STAGE';

/** ISO 8601 date only: yyyy-MM-dd — same shape as Java LocalDate in JSON. */
export type CalendarDate = string;

export type MealType = 'BREAKFAST' | 'LUNCH' | 'DINNER'
  | 'AFTERNOON_SNACK' | 'SUPPER' | 'EVENING_SNACK';

export type TaskTarget = 'W_LOSS'| 'W_GAIN' | 'W_KEEP';

export type TaskStatus = 'PLANNED' | 'ONGOING' | 'COMPLETED' | 'ABORTED';

export type ChartPeriodType = 'DAY' | 'WEEK' | 'MONTH' | 'YEAR';

