# Kakfa POC

## Consumer
```sh
kcat -C -b localhost -K"\t" -t table.book -o beginning
```

## References 
https://stackoverflow.com/questions/46649748/kafka-producer-timeoutexception-expiring-1-records
