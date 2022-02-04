import {DatabaseProviderModel} from "./database-provider.model";

export interface DatabaseConnectModel {
  databaseProvider: DatabaseProviderModel;
  host: string;
  port: number;
  username: string;
  password?: string;
}
