Vue.component("administrator-users", {
    data() {
        return {
            users: [],
            searchField: {
                userName: '',
                name: '',
                surname: '',
                role: ''
            }
        }
    },

    template: `
    <div>
        <h1> users part </h1>

        <form method='post'>

            <input type="text" v-model="searchField.userName" placeholder="username..." >
            <input type="text" v-model="searchField.name" placeholder="Name..." >
            <input type="text" v-model="searchField.surname" placeholder="surname..." >
            <input type="text" v-model="searchField.role" placeholder="role..." >            

        </form>
        <br>


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

    </div>
    `,
    methods: {
        isMatchSearch: function(user){

            // Check for username
            if(! user.userName.match(this.searchField.userName))
                return false;

            // Check for name
            if( ! user.name.match(this.searchField.name))
                return false;

            // Check for surname
            if(! user.surname.match(this.searchField.surname))
                return false;

            // Check for role
            if(! user.role.match(this.searchField.role))
                return false;

            // If i survive all if's now i am matched search
            return true;
        },

    },
    mounted() {
        axios.get('rest/users/getJustUsers')
            .then(response => (this.users = response.data));
    },
    computed: {
        filteredUsers: function () {
            return this.users.filter((user) => {
                return this.isMatchSearch(user);
            });
        }
    },
});