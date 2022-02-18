export enum HttpHeaderType {
    SESSION_TOKEN = 'Session-Token',
    SECURED_ENDPOINT = 'Secured-Endpoint'
}

export interface HttpHeader {
    [header: string]: string | string[];
}
