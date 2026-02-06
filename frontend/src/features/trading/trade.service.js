let balance = 10000;
const holdings = {};

export function executeTrade({ symbol, quantity, action }) {
  const price = getMockPrice(symbol);
  const total = price * quantity;

  if (action === "BUY") {
    if (total > balance) {
      return { success: false, message: "Insufficient balance" };
    }

    balance -= total;
    holdings[symbol] = (holdings[symbol] || 0) + quantity;
    return {
      success: true,
      message: `Bought ${quantity} shares of ${symbol} at $${price} each.`,
      trade: { symbol, quantity, price, action},
      balance
    };
  }

  if (action === "SELL") {
    if ((holdings[symbol] || 0) < quantity) {
      return {
        success: false,
        message: "Not enough shares to sell",
      };
    }

    balance += total;
    holdings[symbol] -= quantity;
    return {
      success: true,
      message: `Sold ${quantity} shares of ${symbol} at $${price} each.`,
      trade: { symbol, quantity, price, action },
      balance,
    };
  }

  return {
    success: false,
    message: "Invalid trade action",
  };
}

function getMockPrice(symbol) {
  // Mock price generation for demonstration purposes
  return Math.floor(Math.random() * 100) + 1; // Price between $1 and $100
}