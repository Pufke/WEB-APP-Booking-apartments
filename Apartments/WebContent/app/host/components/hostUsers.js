Vue.component("host-users", {
    data() {
        return {
            users: [],
        }
    },
    template: `
    <div>
        
    <!-- Table of users -->
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Username </th>
                <th> Password</th>
                <th> Name </th>
                <th> Surname </th>
                <th> Role </th>
            </tr>

            <tr v-for="user in users">
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
});