-- Menu & Funcionality Kibana
INSERT INTO pui_functionality (functionality, subsystem) VALUES('MENU_PUI_DASHBOARD_KIBANA', 'PUI');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_KIBANA', 'es', 1, 'Visualización de BKPI');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_KIBANA', 'en', 1, 'View BKPI');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_KIBANA', 'fr', 1, 'Visualisation de BKPI');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_KIBANA', 'ca', 1, 'Visualització de BKPI');
INSERT INTO pui_menu (node, parent, model, component, functionality, "label", icon_label) VALUES(900, NULL, NULL, 'business-kpi', 'MENU_PUI_DASHBOARD_KIBANA', 'dashboard.kibana', 'far fa-analytics');
INSERT INTO pui_profile_functionality (profile, functionality) VALUES('ADMIN_PUI', 'MENU_PUI_DASHBOARD_KIBANA');
INSERT INTO pui_variable (variable, value, description) VALUES('BUSINESS_KPI_URL', 'http://localhost:30180/', 'Elasticsearch URL');


-- Menu & Funcionality Promtheus
INSERT INTO pui_functionality (functionality, subsystem) VALUES('MENU_PUI_DASHBOARD_PROMETHEUS', 'PUI');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_PROMETHEUS', 'es', 1, 'Visualización de Prometheus');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_PROMETHEUS', 'en', 1, 'View Prometheus');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_PROMETHEUS', 'fr', 1, 'Visualisation de Prometheus');
INSERT INTO pui_functionality_tra (functionality, lang, lang_status, "name") VALUES('MENU_PUI_DASHBOARD_PROMETHEUS', 'ca', 1, 'Visualització de Prometheus');
INSERT INTO pui_menu (node, parent, model, component, functionality, "label", icon_label) VALUES(910, NULL, NULL, 'prometheus', 'MENU_PUI_DASHBOARD_PROMETHEUS', 'dashboard.prometheus', 'far fa-fire');
INSERT INTO pui_profile_functionality (profile, functionality) VALUES('ADMIN_PUI', 'MENU_PUI_DASHBOARD_PROMETHEUS');
INSERT INTO pui_variable (variable, value, description) VALUES('PROMETHEUS_URL', 'http://localhost:50000/', 'Prometheus URL');