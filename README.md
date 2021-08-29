# conversion api general guide

---
This api provides conversion for units.

>Endpoint: `/api/convert`

>Method: `POST`

### Supported operations

**Type** | **Supported Units**
--- |--- |
temperature | `celsuis,kelvin,fahrenheit` |
linear | `inch,yard,nauticalmile,kilometer,foot`

### Sample request

```json
{
    "type": "linear",
    "fromUnit": "kilometer",
    "data": 89,
    "decimalPlace": 8
}
```
>** Note that `type, fromUnit` and `data` are required parameters!
> You also need to provide `type` and `fromUnit` in full. No abbreviations.

### Sample response
```json
{
    "request": {
        "type": "linear",
        "fromUnit": "kilometer",
        "toUnit": null,
        "data": 89.0,
        "decimalPlace": 8
    },
    "payload": {
        "inch": 3503937.00787424,
        "foot": 291994.75065589,
        "yard": 97331.58355226,
        "kilometer": 89.0,
        "nauticalMile": 48.0561552
    }
}
```

