Vue.component('ValidationObserver', VeeValidate.ValidationObserver);
Vue.component('ValidationProvider', VeeValidate.ValidationProvider);

new Vue({
  el: '#main',
  data() {
    return {
      isBusy: false,
      form: {
        name: null
      }
    };
  },
  methods: {
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },
    resetForm() {
      this.form.name = null;
    },
    onSubmit(evt) {
      this.isBusy = true;
      httpSubmit('profiles', null, this.form, this.$bvToast, this.$refs.observer)
        .finally(() => this.isBusy = false);
    }
  }
});
