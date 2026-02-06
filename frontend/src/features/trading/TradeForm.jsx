import { useState } from "react";

export default function TradeForm({ onTrade }) {
  const [symbol, setSymbol] = useState("");
  const [quantity, setQuantity] = useState("");
  const [action, setAction] = useState("BUY");
  const [error, setError] = useState(null);

  function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    const trimmedSymbol = symbol.trim().toUpperCase();
    if (!trimmedSymbol) {
      setError("Symbol is required");
      return;
    }

    const qty = Number(quantity);
    if (!Number.isInteger(qty) || qty <= 0) {
      setError("Quantity must be a positive integer");
      return;
    }

    onTrade({
      symbol: trimmedSymbol,
      quantity: qty,
      action,
    });

    setQuantity("");
  }

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "400px", margin: "0 auto" }}>
      <h2>Trade</h2>

      <div>
        <label>Symbol</label>
        <input 
          type="text"
          value={symbol}
          onChange={(e) => setSymbol(e.target.value)}
          placeholder="AAPL"
          required
        />
      </div>

      <div>
        <label>Quantity</label>
        <input 
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          min="1"
          required
        />
      </div>

      <div>
        <label>Action</label>
        <select value={action} onChange={(e) => setAction(e.target.value)}>
          <option value="BUY">Buy</option>
          <option value="SELL">Sell</option>
        </select>
      </div>

      <button type="submit">Execute Trade</button>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
}