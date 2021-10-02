<script>
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push("/login");
  },
};
</script>
