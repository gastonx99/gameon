echo "Sleeping for a while to let wildfly container fire up before configuring"
sleep 10
CONFIG_FILE=/tmp/standalone.config
echo "Configure wildfly using config file $CONFIG_FILE"
echo "--------------------------------------------------------"
cat $CONFIG_FILE
echo "--------------------------------------------------------"
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --controller=wildfly:9990 --user=admin --password=admin --timeout=60000 --file=$CONFIG_FILE


