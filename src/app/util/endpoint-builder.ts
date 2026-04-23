const BASE_URL = "http://localhost:8080/api/";
export class EndpointBuilder{
  public readonly context: string;
  constructor(...points:string[]) {
    this.context = BASE_URL + points.join('/');
  }
  public build(...points:string[]):string{
    let endpoint =this.buildEndpoint(...points);
    return this.context+endpoint;
  }

  private buildEndpoint(...points:string[]):string{
    return '/'+points.join('/');
  }
}
