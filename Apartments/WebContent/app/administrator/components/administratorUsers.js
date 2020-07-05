Vue.component("administrator-users", {
    data() {
        return {
            users: [],
            searchField: {
                userName: '',
                name: '',
                surname: '',
                role: ''
            },
            hideAddDialog: true,
            newUser: {},
            previewSearch: false,
        }
    },

    template: `
    <div>

        <button type="button" @click=" previewSearch = !previewSearch " class="btn"><i class="fa fa-search" aria-hidden="true"></i> SEARCH </button> 
        <button type="button" @click="addNewHost()" class="btn"><i class="fa fa-plus" aria-hidden="true"></i> ADD NEW </button>
        <br><br>

        <!-- Search users -->
        <div class="searchUsersAdmin" v-if="previewSearch" >
            <form method='post' >

                <input type="text" v-model="searchField.userName" v-bind:class="{filledInput: searchField.userName != '' }" placeholder="username..." >
                <input type="text" v-model="searchField.name" v-bind:class="{filledInput: searchField.name != '' }" placeholder="name..." >
                <input type="text" v-model="searchField.surname" v-bind:class="{filledInput: searchField.surname != '' }" placeholder="surname..." >
                <input type="text" v-model="searchField.role" v-bind:class="{filledInput: searchField.role != '' }" placeholder="role..." >            

            </form>
        </div>
        <!-- End of search for users -->
        <br>


        <!-- Table of all users -->
        <div class="styleForTable" style="width: 70%;" >
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Username </th>
                        <th> Password</th>
                        <th> Name </th>
                        <th> Surname </th>
                        <th> Role </th>
                        <th> Blocking </th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="user in filteredUsers">
                        <td> {{user.userName}}</td>
                        <td> {{user.password}}</td>
                        <td> {{user.name}} </td>
                        <td> {{ user.surname }} </td>
                        <td> {{ user.role }} </td>
                        <td align ="center" >
                            <button v-if="user.blocked == '0' && user.role != 'ADMINISTRATOR' " type="button" @click="blockUser(user)" > Block </button>
                            <button v-if="user.blocked == '1' && user.role != 'ADMINISTRATOR' " type="button" @click="unblockUser(user)" > Unblock </button>
                        </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for all users -->

        <!-- Modal DIALOG section for ADDING HOST -->
        <div id = "addDialogForUsers" v-bind:class="{bgModal: hideAddDialog, bgModalShow: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>

                <form method='post'>
                    
                    <input type="text" v-model="newUser.userName" placeholder="Username" required>
                    <input type="text" v-model="newUser.name" placeholder="Name" >
                    <input type="text" v-model="newUser.surname" placeholder="Surname">
                    <input type="password" v-model="newUser.password" placeholder="Password" required>
                    <input type="password" placeholder="Re Password" required>

                    <button type="button" @click="confirmAdding">Confirm</button>
                    <button type="button" @click="hideAddDialog = !hideAddDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal adding dialog section -->


    </div>
    `,
    methods: {
        addNewHost: function () {
            this.hideAddDialog = !this.hideAddDialog;
        },
        confirmAdding: function () {
            // warning/error if some fields are null or empty
            // ref: https://stackoverflow.com/questions/5515310/is-there-a-standard-function-to-check-for-null-undefined-or-blank-variables-in
            if (!this.newUser.userName || !this.newUser.password || !this.newUser.name || !this.newUser.surname) {
                toastr["warning"]("All field is required", "Watch out !");
                return;

            }
            axios
                .post('rest/users/registration', {
                    "username": this.newUser.userName,
                    "password": this.newUser.password,
                    "name": this.newUser.name,
                    "surname": this.newUser.surname,
                    "role": "HOST"
                })
                .then(response => {
                    location.reload();
                    toastr["success"]("Success created host, try and make one more !!", "Success registration!");
                })
                .catch(err => {
                    console.log("\n\n ------- ERROR -------\n");
                    console.log(err);
                    toastr["error"]("We have alredy user with same username, try another one", "Fail");
                    console.log("\n\n ----------------------\n\n");
                })

        },
        blockUser: function (userParam) {

            axios
                .post('rest/users/blockUser', {
                    user: userParam
                })
                .then(response => {
                    this.users = [];
                    response.data.forEach(el => {
                        this.users.push(el);
                    });
                    toastr["success"]("You make success blocking user !!", "Success blocking!");
                    return this.users;
                });

        },
        unblockUser: function (userParam) {
            axios
                .post('rest/users/unblockUser', {
                    user: userParam
                })
                .then(response => {
                    this.users = [];
                    response.data.forEach(el => {
                        this.users.push(el);
                    });
                    toastr["success"]("You make success unblocking user !!", "Success unblocking!");
                    return this.users;
                });
        },
        isMatchSearch: function (user) {

            // Check for username
            if (!user.userName.match(this.searchField.userName))
                return false;

            // Check for name
            if (!user.name.match(this.searchField.name))
                return false;

            // Check for surname
            if (!user.surname.match(this.searchField.surname))
                return false;

            // Check for role
            if (!user.role.match(this.searchField.role))
                return false;

            // If i survive all if's now i am matched search
            return true;
        },

    },
    mounted() {
        axios.get('rest/users/getJustUsers').then(response => (this.users = response.data));
        axios.get('rest/users/getNewUser').then(response => (this.newUser = response.data));
    },
    computed: {
        filteredUsers: function () {
            return this.users.filter((user) => {
                return this.isMatchSearch(user);
            });
        }
    },
});