UPDATE action_type
SET symbol = REPLACE(symbol, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE attribute
SET value = REPLACE(value, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE attribute_type
SET defaultvalue = REPLACE(defaultvalue, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE menu_item
SET icon = REPLACE(icon, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE rule_type
SET symbol = REPLACE(symbol, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE providerportal
SET template_url = REPLACE(template_url, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE providerportal
SET template_post_url = REPLACE(template_post_url, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE providerportal
SET template_server_url = REPLACE(template_server_url, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE sorted_html
SET wort = REPLACE(wort, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');

UPDATE sorted_html
SET wort = REPLACE(wort, 'http:', 'https:');

UPDATE template_parameter
SET name = REPLACE(name, 'http://qeevee.org:9091', 'https://quest-mill.intertech.de');
