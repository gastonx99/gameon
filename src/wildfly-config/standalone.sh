echo "Sleeping for a while to let wildfly container fire up"
sleep 30
echo "Configure wildfly"
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --controller=wildfly:9990 --user=admin --password=admin --timeout=60000 --file=/tmp/standalone.config


