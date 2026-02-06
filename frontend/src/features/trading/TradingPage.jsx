import { useState } from "react";
import TradeForm from "./TradeForm";
import { executeTrade } from "./trade.service";

export default function TradingPage() {
  const [message, setMessage] = useState(null);
  const [balance, setBalance] = useState(10_000);

  function handleTrade(tradeInput) {
    const result = executeTrade(tradeInput);

    if (!result.success) {
      setMessage({ type: "error", text: result.message });
      return;
    }

    setBalance(result.balance);
    setMessage({ type: "success", text: result.message });
  }

  return (
    <div>
      <h1>Trade</h1>

      <p>
        <strong>Balance:</strong> ${balance}
      </p>

      <TradeForm onTrade={handleTrade} />

      {message && (
        <p style={{ color: message.type === "error" ? "red" : "green" }}>
          {message.text}
        </p>
      )}
    </div>
  );
}
