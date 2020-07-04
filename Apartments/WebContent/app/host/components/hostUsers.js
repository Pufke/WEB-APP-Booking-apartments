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

        <!-- Search -->

        <button type="button" @click=" previewSearch = !previewSearch " class="btn"><i class="fa fa-search" aria-hidden="true"></i> SEARCH </button><br><br>

        <form method='post' v-if="previewSearch" >

            <input type="text" v-model="searchField.role"  placeholder="Guest role..." >
            <input type="text" v-model="searchField.userName"  placeholder="Guest username..." >
            <input type="text" v-model="searchField.gender"  placeholder="Guest gender..." >

        </form>
        <br><br>
        <!-- End of search -->

        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Username </th>
                <th> Password</th>
                <th> Name </th>
                <th> Surname </th>
                <th> Role </th>
            </tr>

            <tr v-for="user in filteredUsers">
                <td> {{user.userName}}</td>
                <td> {{user.password}}</td>
                <td> {{user.name}} </td>
                <td> {{ user.surname }} </td>
                <td> {{ user.role }} </td>
            </tr>

        </table>
        <!-- End of table of users -->

    </div>
    `,
    methods: {
        isMatchSearch: function (user) {

            if(!user.role.match(this.searchField.role))
                return false;

            if(!user.userName.match(this.searchField.userName))
                return false;
            
            if(!user.gender.match(this.searchField.gender))
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