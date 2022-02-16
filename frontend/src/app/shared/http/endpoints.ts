export class Endpoints {
  //@formatter:off

  // Database
  public static readonly DATABASE_PROVIDERS                   = '/database/providers';
  public static readonly DATABASE_HAS_ESTABLISHED_CONNECTION  = '/database/has_established_connection';
  public static readonly DATABASE_CONNECT                     = '/database/connect';
  public static readonly DATABASE_SELECT                      = '/database/select';
  public static readonly DATABASE_SELECTED                    = '/database/selected';
  public static readonly DATABASE_CREATE                      = '/database/create';
  public static readonly DATABASES                            = '/databases';

  // Session
  public static readonly SESSION                              = '/session';

  // Security
  public static readonly LOGIN                                = '/login';
  public static readonly LOGOUT                               = '/deauth';

  // User
  public static readonly USER_ME                              = '/user/me';

  // TO-DO List
  public static readonly TASKS                                = '/tasks';
  public static readonly TASKS_SEARCH                         = '/tasks/search';
  public static readonly TASK                                 = '/task/{id}';
}
