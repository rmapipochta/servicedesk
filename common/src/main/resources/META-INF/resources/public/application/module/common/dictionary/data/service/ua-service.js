export default function ($http, uaSpecStore) {
  return {
    listAccount: function(){
      return $http.get('/account', {}).then(function(response){
        return response.data.map(function(complex){
          return complex.account;
        });
      });
    },

    listComplex: function (start, limit) {
      return $http.get('/account', {})
        .then(function (response) {
          var record, records = [];

          response.data.forEach(function (complexUa) {
            record = {
              $$complexUa: complexUa,
              contexts: [],
              groups: []
            };

            Object.keys(complexUa).forEach(function (uaKey) {
              var ua = complexUa[uaKey], uaSpec;
              if (!ua) {
                return;
              }
              uaSpec = uaSpecStore[uaKey];
              ua.label = uaSpec.label;

              switch (uaSpec.type) {
                case "account":
                  record.login = ua.login;
                  break;
                case "group":
                  record.groups.push(ua);
                  break;
                case "application":
                  record.contexts.push(ua);
              }

            }, this);

            records.push(record);

          }, this);

          return records;

        }.bind(this));
    },

    listRawComplex: function(){
      return $http.get('/account', {}).then(function(response){
        return response.data;
      });
    },

    saveComplex: function(complexUa){

      Object.keys(complexUa).forEach(function(key){
        if(complexUa[key] && !complexUa[key].enable && !complexUa[key].id){
          complexUa[key] = null;
        }
      });

      return $http.post('/account', complexUa).then(function(response){
        return response.data;
      });
    },

    updateComplex: function(complexUa){
      return $http.put('/account', complexUa).then(function(response){
        return response.data;
      });
    }
  }
}