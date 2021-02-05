#!/bin/bash
# usage : /home/furkanince/backup_postgres_unix.sh zekeriyafince 12***21 127.0.0.1 postgres /home/furkanince/backup_postgres_unix.bak
PGUSER=$1 PGPASSWORD=$2 /usr/bin/pg_dump -Fc -U $1 -h $3 -d $4 -f $5



