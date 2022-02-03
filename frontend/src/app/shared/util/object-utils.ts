export class ObjectUtils {
    public static merge<T>(objs: any[]): T {
        return [...objs].reduce(
            (acc, obj) =>
                Object.keys(obj).reduce((a, k) => {
                    acc[k] = acc.hasOwnProperty(k) ? [].concat(acc[k]).concat(obj[k]) : obj[k];
                    return acc;
                }, {}),
            {}
        );
    }

    public static isNil(obj: any): boolean {
        return obj === undefined || obj === null;
    }
}
