
# Проект "Обмен валют"
REST API обменник валют, который позволяет выполнять операции с валютами и обменивать их на другие.


## API Reference

#### Получить все имеющиеся валюты

```http
  GET /currencies
```

#### Получить конкретную валюту по коду

```http
  GET /currency/{code}
```

#### Создать новую валюту

```http
    POST /currencies?name=NAME&code=CODE&sign=sign

```

Также можно использовать ```x-www-from-urlencoded``` для заполнения полей ```name```,
```code``` и ```sign```

#### Получить все обменные курсы валюту

```http
    GET /exchangeRates
```

#### Получить обменный курс валют

```http
    GET /exchangeRate/{code1}{code2}
```

#### Обновить обменный курс валют

```http
    PATCH /exchangeRate/{code1}{code2}?rate={rate}
```

#### Совершить обмен

```http
    GET /exchangeRate?from={v1}&to={v2}&amount={amount}
```

