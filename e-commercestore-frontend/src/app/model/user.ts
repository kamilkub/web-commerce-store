export class User {
     id: Number;
     email: String;
     username: String;
     password: String;
     isAccountEnabled:boolean;
    
    setEmail(email: String){
        this.email = email;
    }

    setUsername(username: String){
        this.username = username;
    }

    setPassword(password: String){
        this.password = password;
    }


}
