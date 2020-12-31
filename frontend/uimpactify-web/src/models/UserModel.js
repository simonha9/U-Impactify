class UserModel {

    constructor(username) {
        this._username = username;
    }

    get username() {
        return this._username;
    }

}

export default UserModel;