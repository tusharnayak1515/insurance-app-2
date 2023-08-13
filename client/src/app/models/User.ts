class User {
    constructor(
      public userId: number | null,
      public username: string,
      public email: string,
      public mobile: string,
      public password: string,
      public address: string | null,
      public role:string | null
    ) {}
  }
  
  export default User;
  