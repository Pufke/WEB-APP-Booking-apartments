Vue.component("host-users", {
    data() {
        return {
            users: [],
            searchField: '',
        }
    },
    template: `
    <div>
        
    <!-- Table of users -->

        <input type="text" v-model="searchField"  placeholder="Role, username, gender... " >

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
        isMatchSearch: function(user){
            
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
                return ( user.role.match(this.searchField) || user.userName.match(this.searchField) || user.gender.match(this.searchField) );
            });
        }
    },
});