//= wrapped

angular
    .module("fr.isima.stackoverflow")
    .factory("Post", Post);

function Post($resource) {
    var Post = $resource(
        "post/:id",
        {"id": "@id"},
        {"update": {method: "PUT"}, "list": {method: "GET", isArray: true}}
    );
    return Post;
}
