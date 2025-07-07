-- 1. Progetti
insert into project(id, name) values(nextval('project_seq'), 'LTRAD');
insert into project(id, name) values(nextval('project_seq'), 'FIRE');
insert into project(id, name) values(nextval('project_seq'), 'VOLCANO');

-- 2. Utenti
insert into app_user(id, name, surname, date_of_birth, phone_number)values(1, 'Flaminia', 'Balduini', '2003-10-16', '3737397589');
insert into app_user(id, name, surname, date_of_birth, phone_number)values(2, 'Flaminia', 'Balduini', '2003-10-16', '3737397589');
insert into app_user(id, name, surname, date_of_birth, phone_number)values(3, 'Luca', 'Bussi', '2003-09-17', '3315988152');
insert into app_user(id, name, surname, date_of_birth, phone_number)values(4, 'Jhon', 'Herrera', '2004-01-30', '3887258823');

-- 3. Credenziali (devono esistere gli utenti e il progetto)
insert into credentials(id, project_id, user_id, email, password, role, username, visible_username)values(1, 1, 1, 'fbalduini@icloud.com','$2a$10$cHSpwVdP8S33zR6PHIfvs.g9TZl6QbhHX9KTayP/91cxVScdb/c.W', 'ADMIN', 'mimi16|ADMIN', 'mimi16');
insert into credentials(id, project_id, user_id, email, password, role, username, visible_username)values(2, 1, 2, 'flamy003@gmail.com','$2a$10$cHSpwVdP8S33zR6PHIfvs.g9TZl6QbhHX9KTayP/91cxVScdb/c.W', 'LTRAD', 'mimi|LTRAD', 'mimi');
insert into credentials(id, project_id, user_id, email, password, role, username, visible_username)values(3, 51, 3, 'luca.bussi@outlook.it','$2a$10$cHSpwVdP8S33zR6PHIfvs.g9TZl6QbhHX9KTayP/91cxVScdb/c.W', 'FIRE', 'kuca|FIRE', 'kuca');
insert into credentials(id, project_id, user_id, email, password, role, username, visible_username)values(4, 101, 4, 'jhon30.herrera@gmail.com','$2a$10$cHSpwVdP8S33zR6PHIfvs.g9TZl6QbhHX9KTayP/91cxVScdb/c.W', 'VOLCANO', 'jem|VOLCANO', 'jem');

-- 4. Gruppi (devono esistere le credentials, e il progetto)
insert into app_group(credentials_id, id, project_id, name)values (2, 1, 1, 'Roma');
insert into app_group(credentials_id, id, project_id, name)values (2, 2, 1, 'Milano');

insert into type_of_device(id, name)values(1, '4Spark 1')
insert into type_of_device(id, name)values(2, '4Spark 2')
insert into type_of_device(id, name)values(3, '4Spark 3')


insert into project_tods(project_id, tods_id)values(1, 1)
insert into project_tods(project_id, tods_id)values(51, 2)
insert into project_tods(project_id, tods_id)values(101, 3)


-- 5. Dispositivi (devono esistere i gruppi e i progetti)

/* MIMI - Roma, gruppo compatto */
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.850001, 12.460101, 1, 1, 1, 'flamy003@gmail.com', '21:06:1C:EC:E7:20', 'Blue1', 1);
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.850302, 12.460402, 1, 2, 1, 'flamy003@gmail.com', '34:09:1C:EC:E7:21', 'Blue2', 1);
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.850503, 12.460703, 1, 3, 1, 'flamy003@gmail.com', '56:08:1C:EC:E7:22', 'Blue3', 1);
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.850804, 12.461004, 1, 4, 1, 'flamy003@gmail.com', '15:05:1B:EC:E7:23', 'Blue4', 1);
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.851005, 12.461305, 1, 5, 1, 'flamy003@gmail.com', '17:04:1C:EC:E7:24', 'Blue5', 1);
insert into device(latitude, longitude, group_id, id, project_id, email_owner, mac_address, name, tod_id)values(41.851206, 12.461606, 1, 6, 1, 'flamy003@gmail.com', '18:03:1C:EC:E7:25', 'Blue6', 1);

/* KUCA - sparsi nel nord Italia */
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(45.440001, 10.995001, 7, 51, 'luca.bussi@outlook.it','11:06:1C:EC:E8:26', 'Red1', 2);  -- Verona
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(44.494889, 11.342616, 8, 51, 'luca.bussi@outlook.it','14:06:1C:EC:E8:27', 'Red2', 2);  -- Bologna
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(45.070339, 7.686864, 9, 51, 'luca.bussi@outlook.it','15:06:1C:EC:E8:28', 'Red3', 2);   -- Torino
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(46.062008, 11.121083, 10, 51, 'luca.bussi@outlook.it', '23:06:1C:EC:E8:29', 'Red4', 2); -- Trento
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(45.649526, 13.776818, 11, 51, 'luca.bussi@outlook.it', '39:06:1C:EC:E8:30', 'Red5', 2); -- Trieste
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(44.801485, 10.327903, 12, 51, 'luca.bussi@outlook.it', '4A:06:1C:EC:E8:31', 'Red6', 2); -- Parma

/* JEM - sud e isole */
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(40.851775, 14.268124, 13, 101, 'jhon30.herrera@gmail.com', '26:06:1C:EC:E7:32', 'Amber1', 3); -- Napoli
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(38.115688, 13.361267, 14, 101, 'jhon30.herrera@gmail.com', '37:06:1C:EC:E7:33', 'Amber2', 3); -- Palermo
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(37.507877, 15.083030, 15, 101, 'jhon30.herrera@gmail.com', '58:06:1C:EC:E7:34', 'Amber3', 3); -- Catania
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(39.223841, 9.121661, 16, 101, 'jhon30.herrera@gmail.com', '6B:06:1C:EC:E7:35', 'Amber4', 3);  -- Cagliari
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(40.639470, 17.694360, 17, 101, 'jhon30.herrera@gmail.com', '7C:06:1C:EC:E7:36', 'Amber5', 3); -- Lecce
insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(38.193791, 15.554045, 18, 101, 'jhon30.herrera@gmail.com', '8D:06:1C:EC:E7:37', 'Amber6', 3); -- Messina

insert into device(latitude, longitude, id, project_id, email_owner, mac_address, name, tod_id)values(41.85490586987041, 12.470265684183323, 19, 101, 'prova@gmail.com', '8D:06:1C:EC:E7:59', 'Prova', 3); -- Messina

insert into spec(id, measurement, unit_of_measurement, component)values(1, 'Temperature', '°C' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(2, 'Relative Humidity', '%RH' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(3, 'VOC', 'Index (0-500)' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(4, 'PM 1.0', 'µg/m³' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(5, 'PM 2.5', 'µg/m³' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(6, 'PM 4.0', 'µg/m³' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(7, 'PM 10.0', 'µg/m³' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(8, 'NOx', 'index' ,'Sen5x')
insert into spec(id, measurement, unit_of_measurement, component)values(9, 'Temperature', '°C' ,'SCD30')
insert into spec(id, measurement, unit_of_measurement, component)values(10, 'PM 1.0', 'µg/m³' ,'SPS30')
insert into spec(id, measurement, unit_of_measurement, component)values(11, 'PM 2.5', 'µg/m³' ,'SPS30')
insert into spec(id, measurement, unit_of_measurement, component)values(12, 'PM 4.0', 'µg/m³' ,'SPS30')
insert into spec(id, measurement, unit_of_measurement, component)values(13, 'PM 10.0', 'µg/m³' ,'SPS30')
insert into spec(id, measurement, unit_of_measurement, component)values(14, 'Temperature', '°C' ,'Dallas')

insert into type_of_device_specs(specs_id, type_of_device_id)values(1, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(2, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(3, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(4, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(5, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(6, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(7, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(8, 1)
insert into type_of_device_specs(specs_id, type_of_device_id)values(9, 3)
insert into type_of_device_specs(specs_id, type_of_device_id)values(10, 3)
insert into type_of_device_specs(specs_id, type_of_device_id)values(11, 3)
insert into type_of_device_specs(specs_id, type_of_device_id)values(12, 3)
insert into type_of_device_specs(specs_id, type_of_device_id)values(13, 3)
insert into type_of_device_specs(specs_id, type_of_device_id)values(14, 2)



-- 6. Reset sequenze
SELECT setval('app_user_seq', (SELECT MAX(id) FROM app_user));
SELECT setval('credentials_seq', (SELECT MAX(id) FROM credentials));
SELECT setval('app_group_seq', (SELECT MAX(id) FROM app_group));
SELECT setval('device_seq', (SELECT MAX(id) FROM device));
SELECT setval('type_of_device_seq', (SELECT MAX(id) FROM type_of_device));
SELECT setval('spec_seq', (SELECT MAX(id) FROM spec));



