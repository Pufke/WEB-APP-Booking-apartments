Vue.component("host-users", {
    data() {
        return {
            users: [],
            searchField: {
                role: '',
                userName: '',
                gender: '',
            },
            previewSearch: false,
        }
    },
    template: `
    <div>
        
    <!-- Table of users -->
    
        <button type="button" @click=" previewSearch = !previewSearch " class="btn"><i class="fa fa-search" aria-hidden="true"></i> SEARCH </button><br><br>
        <!-- Search -->

        <div class="searchUsers" v-if="previewSearch" >
            <form method='post'>

                <input type="text" v-model="searchField.userName" v-bind:class="{filledInput: searchField.userName != '' }" placeholder="guest username..." >
                <input type="text" v-model="searchField.gender" v-bind:class="{filledInput: searchField.gender != '' }" placeholder="guest gender..." >

            </form>
        </div>
        <br><br>
        <!-- End of search -->


        <!-- Table of guest of this host -->
        <div class="styleForTable" style="width: 70%;">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Username </th>
                        <th> Password</th>
                        <th> Name </th>
                        <th> Surname </th>
                        <th> Role </th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="user in filteredUsers">
                        <td> {{user.userName}}</td>
                        <td> {{user.password}}</td>
                        <td> {{user.name}} </td>
                        <td> {{ user.surname }} </td>
                        <td> {{ user.role }} </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for guest of this host -->

    </div>
    `,
    methods: {
        isMatchSearch: function (user) {

            if(!user.userName.toLowerCase().match(this.searchField.userName.toLowerCase()))
                return false;
            
            if(!user.gender.toLowerCase().match(this.searchField.gender.toLowerCase()))
                return false;

            // If i survive all if's now i am matched search
            return true;
        },
    },
    mounted() {
        axios
            .get('rest/users/getGuestsOfHost')
            .then(response => {
                this.users = [];
                response.data.forEach(el => {
                    this.users.push(el);
                });
                return this.users;
            });
    },
    computed: {
        filteredUsers: function () {
            return this.users.filter((user) => {
                return this.isMatchSearch(user);
            });
        }
    },
});