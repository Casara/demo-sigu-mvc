function isNullOrUndefined(value) {
  return value === null || value === undefined;
}

function isEmptyArray(arr) {
  return Array.isArray(arr) && arr.length === 0;
}

function isBlank(value) {
  return isNullOrUndefined(value) || !String(value).trim().length;
}

function violationsToErrors(violations) {
  var errors = {};
  violations.forEach(v => {
    if (!errors.hasOwnProperty(v.field)) {
      errors[v.field] = [v.message];
    }
  });
  return errors;
}

function setValue(obj, value, path) {
  var i;
  var path = path.split('.');
  for (i = 0; i < path.length - 1; i++) {
    obj = obj[path[i]];
  }
  obj[path[i]] = value;
}

Vue.directive('init', {
  bind(el, binding, vnode) {
    if (vnode.data.model) {
      setValue(vnode.context, binding.value, vnode.data.model.expression);
    } else {
      var vModel = vnode.data.directives.find(d => d.rawName == 'v-model');
      if (vModel) {
        setValue(vnode.context, binding.value, vModel.expression);
      }
    }
  }
});

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

const fetchDefaultOptions = {
  headers: {'Accept': 'application/json'},
  method: 'GET',
  mode: 'cors',
  cache: 'default'
};

function showErrorResponseDetailsInToast(json, $bvToast) {
  if (!isBlank(json.detail)) {
    $bvToast.toast(json.detail, {
      title: 'Ocorreu um ploblema!',
      autoHideDelay: 5000,
      solid: true,
      variant: 'danger'
    });
  }
}

function httpGet(path, callback) {
  return fetch('/api/v1/' + path, fetchDefaultOptions)
    .then(response => {
      if (response.ok) {
        response.json().then(callback);
      }
    });
}

function httpSubmit(path, id, body, $bvToast, observer) {
  var method = 'POST';
  var url = '/api/v1/' + path;
  if (!isBlank(id)) {
    method = 'PUT';
    url += '/' + id;
  }

  return fetch(url, {
    body: JSON.stringify(body),
    headers: {'Content-Type': 'application/json'},
    method: method,
    mode: 'cors',
  }).then(response => {
    if (response.ok) {
      window.location.pathname = '/' + path;
    } else {
      response.json().then(json => {
        showErrorResponseDetailsInToast(json, $bvToast);
        if (!isEmptyArray(json.violations)) {
          observer.setErrors(violationsToErrors(json.violations));
        }
      });
    }
  });
}

function httpDelete(path, callback) {
  if (confirm("Deseja realmente excluir esse registro?")) {
    fetch('/api/v1/' + path, {method: 'DELETE', mode: 'cors'})
      .then(callback);
  }
}

new Vue({
  el: '#flashError',
  mounted() {
    this.showErrorInToast();
  },
  methods: {
    showErrorInToast: function () {
      if (!isBlank(this.$el.innerText)) {
        showErrorResponseDetailsInToast({
          detail: this.$el.innerText
        }, this.$bvToast);
      }
    }
  }
});

new Vue({
  el: '#navbar'
});
