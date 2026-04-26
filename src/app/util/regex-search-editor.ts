export function markWord(text: string, searchKey: string): string {
  if (!searchKey) {
    return escapeHtml(text);
  }
  const escapedSearch = escapeRegex(searchKey);
  const rx = new RegExp(`(${escapedSearch})`, 'gi');
  return escapeHtml(text).replace(rx, '<mark>$1</mark>');
}


function escapeHtml(value: string): string {
  return value
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;')
    .replaceAll("'", '&#39;');
}

function escapeRegex(value: string): string {
  return value.replaceAll(/[.*+?^${}()|[\]\\]/g, String.raw`\$&`);
}
