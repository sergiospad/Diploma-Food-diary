export function markWord(text: unknown, searchKey: unknown): string {
  const sourceText = normalizeToString(text);
  const sourceSearchKey = normalizeToString(searchKey);

  if (!sourceSearchKey) {
    return escapeHtml(sourceText);
  }

  const escapedSearch = escapeRegex(sourceSearchKey);
  const rx = new RegExp(`(${escapedSearch})`, 'gi');
  return escapeHtml(sourceText).replace(rx, '<mark>$1</mark>');
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

function normalizeToString(value: unknown): string {
  if (typeof value === 'string') {
    return value;
  }
  if (value == null) {
    return '';
  }
  if (typeof value === 'object' && 'name' in value && typeof (value as { name?: unknown }).name === 'string') {
    return (value as { name: string }).name;
  }
  return String(value);
}
