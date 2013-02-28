package cache.memcached

class Message implements Serializable {

    private static final long serialVersionUID = 1

    String title
    String body

    String toString() {
        "$title: $body"
    }
}