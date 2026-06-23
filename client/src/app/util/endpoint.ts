const BASE_URL = "http://localhost:8080/api/";

class EndpointBuilder{
  private readonly endpoint: string;
  private readonly params:string[];
  constructor(endpoint: string, params:string[]) {
    this.endpoint = endpoint;
    this.params = params;
  }
  public points(...points:string[]):EndpointBuilder{
    let endpoints = '/'+points.join('/');
    return new EndpointBuilder(this.endpoint+endpoints, []);
  }

  public addParam(nameParam:string, valueParam:string):EndpointBuilder{
    let param = `${nameParam}=${valueParam}`;
    this.params.push(param);
    return new EndpointBuilder(this.endpoint, this.params);
  }

  public build(): string {
    const par = this.params.join('&');
    return par.length > 0 ? `${this.endpoint}?${par}` : this.endpoint;
  }


}
export class Endpoint {
  public readonly context: string;
  constructor(...points:string[]) {
    this.context = BASE_URL + points.join('/');
  }

  public builder(): EndpointBuilder {
    return new EndpointBuilder(this.context, [])
  }
}
