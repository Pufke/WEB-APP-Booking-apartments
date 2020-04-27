Vue.component("host-header",{
    data: function(){
        return{
            fleg:0
        }
    }
    ,
    template: `
    <div id="divHeaderGuest">
        <nav>
            <ul>
                <li><router-link to="/home" exact>Home Page</router-link></li>
                <li><router-link to="/profile" exact>Profile</router-link></li>
                <li><router-link to="/users" exact>Users</router-link></li>
                <li><router-link to="/hostActiveApartments" exact>Active apartments</router-link></li> 
                <li><router-link to="/hostInactiveApartments" exact>Inactive apartments</router-link></li>             
                <li><router-link to="/reservations" exact>Reservations</router-link></li> 
                <li><router-link to="/commentsAndReviews" exact>Comments and Reviews</router-link></li> 
                <li><button @click="logout" > Log out </button></li>
                
            </ul>
        </nav>

    </div>		  
    
    `,
    methods: {
        logout: function(event){
            event.preventDefault
            axios
            .get('rest/logout/someone')
            .then(response => {
                location.href = "/Apartments/#/login";
            })
            .catch(err => {
                console.log(err);
                alert('Error during log out');
            })
        }
    },
});

