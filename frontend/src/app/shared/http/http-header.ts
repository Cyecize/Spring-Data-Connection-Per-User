export enum HttpHeaderType {
    AUTHORIZATION_TOKEN = 'Authorization-Token',
    SECURED_ENDPOINT = 'Secured-Endpoint'
}

export interface HttpHeader {
    [header: string]: string | string[];
}
