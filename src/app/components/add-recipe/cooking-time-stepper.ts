

export class CookingTimeStepper {
  constructor(
    private readonly min = 5,
    private readonly step = 5,
  ){}

  parseInput(raw:string):number|null{
    if(raw.trim()=='') return null;
    const n = Number(raw.trim());
    return Number.isNaN(n)? null: Math.max(this.min, n);
  }

  snapOnBlur(raw:string):number{
    const n = Number(raw);
    if (raw.trim() === '' || Number.isNaN(n)) return this.min;
    return Math.max(this.min, Math.round(n / this.step) * this.step);
  }
}
