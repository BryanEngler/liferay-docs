---
header-id: installing-x-pack-monitoring-for-elasticsearch-6
---

# Installing X-Pack Monitoring for Elasticsearch 6.5

[TOC levels=1-4]

To monitor Elasticsearch, use X-Pack Monitoring. First 
[install X-Pack onto Elasticsearch](/docs/7-0/deploy/-/knowledge_base/d/securing-elasticsearch-6-with-x-pack)
and configure security if you're using X-Pack's security features. Then come
back here for instructions on installing and configuring Kibana (the monitoring
server) with X-Pack so that Elasticsearch, Kibana, and @product@ can
communicate effortlessly and securely (if you're using X-Pack Security). A
Liferay Enterprise Search subscription is necessary for this integration.
Contact 
[Liferay's Sales department for more information](https://www.liferay.com/contact-us#contact-sales).

| **Compatibility:** To use X-Pack Security and/or Monitoring with Elasticsearch
| 6.5 and @product@, you must use the proper connector to Elasticsearch.  
| 
| The [_Liferay Connector to Elasticsearch 6_, version
| 1.1.0+](https://customer.liferay.com/downloads?p_p_id=com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_productAssetCategoryId=118191013&_com_liferay_osb_customer_downloads_display_web_DownloadsDisplayPortlet_fileTypeAssetCategoryId=118191060) is required to set up Elasticsearch
| 6.5 with security and monitoring.

1.  Tell Elasticsearch to enable data collection.

2.  Download and install Kibana.

3.  Configure Kibana with the proper security settings.

4.  Download and install the 
    [Liferay Enterprise Search Monitoring](https://www.liferay.com/marketplace) 
   .

5.  Configure the connector to communicate with Elasticsearch.

For the X-Pack security procedure, refer to the 
[X-Pack security article](/docs/7-0/deploy/-/knowledge_base/d/securing-elasticsearch-6-with-x-pack).

## Enable Data Collection

Monitoring is enabled on Elasticsearch by default, but data collection isn't.
Enable data collection by adding this line to `elasticsearch.yml`.

    xpack.monitoring.collection.enabled: true

Now install Kibana.

## Install Kibana

Make sure to install the correct version of Kibana. Check the 
[Liferay Enterprise Search compatibility matrix](https://help.liferay.com/hc/en-us/articles/360016511651#Liferay-Enterprise-Search)
for details.

1.  [Download Kibana](https://www.elastic.co/downloads/kibana)
    and extract it. The root folder is referred to as *Kibana Home*.

2.  Tell Kibana where to send monitoring data by setting Elasticsearch's URL in
    `kibana.yml`:

        elasticsearch.url: "http://localhost:9200"

    If encryption is enabled on Elasticsearch, this is an `https` URL.

3.  If not using X-Pack security, start Kibana by opening a command prompt to 
    Kibana Home and entering this command:

        ./bin/kibana

If you're using X-Pack's security features on the Elasticsearch server, there's
additional configuration required before starting Kibana.

### Configure Kibana with Authentication

If X-Pack requires authentication to access the Elasticsearch cluster, follow
these steps or refer to 
[Elastic's documentation](https://www.elastic.co/guide/en/kibana/6.5/monitoring-xpack-kibana.html). 

1.  Set the password for the built-in `kibana` user in `Kibana
    Home/config/kibana.yml`:

        elasticsearch.username: "kibana"
        elasticsearch.password: "liferay"

    The password is whatever you set it to when initially setting up X-Pack.
    Once Kibana is installed, you can change the built-in user passwords from the
    *Management* user interface.

2.  If you're not encrypting communication with the Elasticsearch cluster, start
    Kibana with 

        ./bin/kibana

    and navigate to `localhost:5601`. Log in with a user who has the
    `kibana_user` role.

### Configuring Kibana with Encryption

Follow these steps to configure Kibana if X-Pack encrypts communication with the
Elasticsearch cluster. Consult 
[Elastic's guide](https://www.elastic.co/guide/en/kibana/6.2/using-kibana-with-security.html#using-kibana-with-security)
for more information.

Add these settings to `kibana.yml`:

    xpack.security.encryptionKey: "xsomethingxatxleastx32xcharactersx"
    xpack.security.sessionTimeout: 600000

    elasticsearch.ssl.verificationMode: certificate
    elasticsearch.url: "https://localhost:9200"
    elasticsearch.ssl.certificateAuthorities: [ "/path/to/ca.crt" ]

    server.ssl.enabled: true
    server.ssl.certificate: /path/to/[Elasticsearch Home]/config/localhost.crt
    server.ssl.key: /path/to/[Elasticsearch Home]/config/localhost.key

For more information about monitoring and security best practices in a clustered
environment, refer to 
[Elastic's documentation](https://www.elastic.co/guide/en/elasticsearch/reference/6.5/es-monitoring.html).

After this step you can access Kibana at `https://localhost:5601` and sign in
with a Kibana user. The last step is to hook Kibana up with @product@.

## Configuring the Liferay Enterprise Search Monitoring app

If you have a Liferay Enterprise Search subscription, download the Liferay
Enterprise Search Monitoring. Install the LPKG file by
copying it into the `Liferay Home/deploy` folder. That's all there is to it.

1.  Once the connector is installed and Kibana and Elasticsearch are securely
    configured, create a 
    [configuration file](/docs/7-0/user/-/knowledge_base/u/understanding-system-configuration-files)
    named

        com.liferay.portal.search.elasticsearch6.xpack.monitoring.web.internal.configuration.XPackMonitoringConfiguration.config

2.  Place these settings in the `.config` file:

        kibanaPassword="liferay"
        kibanaUserName="elastic"
        kibanaURL="http://localhost:5601"

    Alternatively, configure the monitoring adapter from the Control Panel.
    Navigate to *Configuration* &rarr; *System Settings* and find the X-Pack
    Monitoring entry in the Foundation category. All the configuration 
    options for the monitoring connector appear there.

    The values differ depending on your Kibana configuration. For example,
    `kibanaURL="https://localhost:5601"` if using X-Pack Security features.

3.  Deploy this configuration file to `Liferay Home/osgi/configs`, and the
    settings are picked up by your running instance. There's no need to restart
    the server.

4.  There are two more settings to add to Kibana itself. The first forbids 
    Kibana from rewriting requests prefixed with `server.basePath`. The second
    sets Kibana's base path for the Monitoring portlet to act as a proxy for
    Kibana's monitoring UI. Add this to `kibana.yml`:

        server.rewriteBasePath: false
        server.basePath: "/o/portal-search-elasticsearch-xpack-monitoring/xpack-monitoring-proxy"

    Note that once you set the `server.basePath`, you cannot access the Kibana
    UI through Kibana's URL (for example, `https://localhost:5601`). All access
    to the Kibana UI is via the monitoring portlet, which is only accessible to
    logged in @product@ users. Navigate directly to the portlet using this URL:
    [http://localhost:8080/o/portal-search-elasticsearch-xpack-monitoring/xpack-monitoring-proxy/app/monitoring](http://localhost:8080/o/portal-search-elasticsearch-xpack-monitoring/xpack-monitoring-proxy/app/monitoring)

5.  Because you're using the Monitoring portlet in @product@ as a proxy to
    Kibana's UI, if you are using X-Pack Security, you must configure the
    application server's startup JVM parameters to recognize a valid
    *truststore* and *password*.

    First, navigate to Elasticsearch Home and generate a PKSC#12 certificate
    from the CA you created when setting up X-Pack security:

        ./bin/elasticsearch-certutil cert --ca-cert /path/to/ca.crt --ca-key /path/to/ca.key --ip 127.0.0.1 --dns localhost --name localhost --out /path/to/Elasticsearch_Home/config/localhost.p12

    Next use the `keytool` command to generate a truststore:

        keytool -importkeystore -deststorepass liferay -destkeystore /path/to/truststore.jks -srckeystore /path/to/Elasticsearch_Home/config/localhost.p12 -srcstoretype PKCS12 -srcstorepass liferay

    Add the trustore path and password to your application server's startup JVM
    parameters. Here are example truststore and path parameters for appending to
    a Tomcat server's `CATALINA_OPTS`:

        -Djavax.net.ssl.trustStore=/path/to/truststore.jks -Djavax.net.ssl.trustStorePassword=liferay

Restart @product@ and Kibana.

## Monitoring in @product@

Once Kibana and X-Pack are successfully installed and configured and all the
servers are up and running, add the X-Pack Monitoring portlet to a page:

1.  Open the *Add* menu on a page and choose *Applications*

2.  Search for *monitoring* and drag the *X-Pack Monitoring* application from
    the Search category onto the page.

See the Elastic documentation for information on 
[monitoring Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/6.5/es-monitoring.html).



