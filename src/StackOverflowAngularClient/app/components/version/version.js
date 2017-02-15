'use strict';

angular.module('segFault.version', [
  'segFault.version.interpolate-filter',
  'segFault.version.version-directive'
])

.value('version', '0.1');
