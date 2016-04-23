angular.module("common.dictionary.data")
  .factory("uaService", function ($http, uaSpecStore) {
    return {
      listComplex: function (start, limit) {
        return $http.get('/ua', {})
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
                  case "global":
                    //angular.extend(record, ua);
                    record.login = ua.login;
                    break;
                  case "group":
                    record.groups.push(ua);
                    break;
                  case "context":
                    record.contexts.push(ua);
                }

              }, this);

              records.push(record);

            }, this);

            return records;

          }.bind(this));
      },

      listRawComplex: function(){
        return $http.get('/ua', {}).then(function(response){
          return response.data;
        });
      },

      saveComplex: function(complexUa){
        return $http.post('/ua', complexUa).then(function(response){
          return response.data;
        });
      },

      updateComplex: function(complexUa){
        return $http.put('/ua', complexUa).then(function(response){
          return response.data;
        });
      }
    }
  });