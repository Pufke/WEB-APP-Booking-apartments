Vue.component("host-CommentsAndReviews", {
    data() {
        return {
            comments: []
        }
    },
    template: `
    <div id = "styleForApartmentsView" >
        <h1> Hello from comments </h1>

        <ul>
            <li v-for="comment in comments">
                
                <table class="tableInCards">
                    <tr>
                        <td> Comment: </td>
                        <td> {{ comment.txtOfComment }} </td>
                    </tr>

                    <tr>
                        <td> Rating </td>
                        <td> {{ comment.ratingForApartment }}/10 </td>
                    </tr>
                </table>

                <button v-if="!comment.isAvailableToSee" type="button" @click="showComment(comment)"><i class="fa fa-eye" aria-hidden="true"></i> Show </button>
                <button v-if="comment.isAvailableToSee" type="button" @click="hideComment(comment)"><i class="fa fa-eye-slash" aria-hidden="true"></i> Hide </button>
            </li>
        </ul>

        <br>

        <!-- Table of comments for host apartments -->
        <div class="styleForTable">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Text </th>
                        <th> Rating </th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="comment in comments">
                        <td> {{ comment.txtOfComment }} </td>
                        <td> {{ comment.ratingForApartment }} </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for comments of host apartments -->

    </div>
    `,
    methods: {
        hideComment: function (commentParam) {
            axios
                .post('rest/comments/hideComment', {
                    comment: commentParam
                })
                .then(response => {
                    this.comments = [];
                    response.data.forEach(el => {
                        this.comments.push(el);
                    });
                    toastr["success"]("You make successful hide of comment !!", "Successful hiding!");

                    return this.comments;
                });
        },
        showComment: function (commentParam) {
            axios
                .post('rest/comments/showComment', {
                    comment: commentParam
                })
                .then(response => {
                    this.comments = [];
                    response.data.forEach(el => {
                        this.comments.push(el);
                    });
                    toastr["success"]("You make successful show of comment !!", "Successful showing!");

                    return this.comments;
                });
        }
    },
    mounted() {
        axios
            .get('rest/comments/getMyComments')
            .then(response => {
                this.comments = [];
                response.data.forEach(el => {
                    this.comments.push(el);
                });
                return this.comments;
            });
    },
});