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
                <h2> Author ID: {{ comment.guestAuthorOfCommentID}} </h2>
                <h2> Apartment ID: {{ comment.commentForApartmentID }} </h2>
                <h2> Text: {{ comment.txtOfComment }} </h2>
                <h2> Rating: {{ comment.ratingForApartment }} </h2>

                <button v-if="comment.isAvailableToSee" type="button" @click="hideComment(comment)"> Hide </button>
                <button v-if="!comment.isAvailableToSee" type="button" @click="showComment(comment)"> Show </button>
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
                    toastr["success"]("You make success hide of comment !!", "Success hiding!");

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
                    toastr["success"]("You make success show of comment !!", "Success showing!");

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