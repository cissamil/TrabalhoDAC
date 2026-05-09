db = db.getSiblingDB('ms_auth_db');

db.usuarios.insertMany([
  {
    userId:'a1b2c3d4-1001-4000-8000-000000000001',
    email:'cli1@bantads.com.br',
    senha:'tads',
    tipo_usuario:'CLIENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1002-4000-8000-000000000002',
    email:'cli2@bantads.com.br',
    senha:'tads',
    tipo_usuario:'CLIENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1003-4000-8000-000000000003',
    email:'cli3@bantads.com.br',
    senha:'tads',
    tipo_usuario:'CLIENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1004-4000-8000-000000000004',
    email:'cli4@bantads.com.br',
    senha:'tads',
    tipo_usuario:'CLIENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1005-4000-8000-000000000005',
    email:'cli5@bantads.com.br',
    senha:'tads',
    tipo_usuario:'CLIENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1001-4000-8000-000000000006',
    email:'ger1@bantads.com.br',
    senha:'tads',
    tipo_usuario:'GERENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1002-4000-8000-000000000007',
    email:'ger2@bantads.com.br',
    senha:'tads',
    tipo_usuario:'GERENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1003-4000-8000-000000000008',
    email:'ger3@bantads.com.br',
    senha:'tads',
    tipo_usuario:'GERENTE',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
  {
    userId:'a1b2c3d4-1004-4000-8000-000000000009',
    email:'adm1@bantads.com.br',
    senha:'tads',
    tipo_usuario:'ADMIN',
    _class: 'br.ufpr.dataprovider.adapter.domain.UsuarioEntity'},
]);

db.usuarios.createIndex({userId: 1}, {unique: true});
print('### Banco ms_auth_db inicializado com sucesso! ###');
