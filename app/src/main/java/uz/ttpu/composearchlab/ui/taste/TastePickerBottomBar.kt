package uz.ttpu.composearchlab.ui.taste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TastePickerBottomBar(
    likedCount: Int,
    required: Int,
    canContinue: Boolean,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(48.dp)
                    ) {
                        val progressValue = (likedCount.toFloat() / required).coerceIn(0f, 1f)
                        CircularProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "$likedCount",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Pick $required artists you like",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onSkipClick) {
                    Text("Later")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onContinueClick,
                    enabled = canContinue
                ) {
                    Text("Continue")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TastePickerBottomBar0LikedPreview() {
    TastePickerBottomBar(
        likedCount = 0,
        required = REQUIRED_LIKES,
        canContinue = false,
        onContinueClick = {},
        onSkipClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TastePickerBottomBar2LikedPreview() {
    TastePickerBottomBar(
        likedCount = 2,
        required = REQUIRED_LIKES,
        canContinue = false,
        onContinueClick = {},
        onSkipClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TastePickerBottomBar3LikedPreview() {
    TastePickerBottomBar(
        likedCount = 3,
        required = REQUIRED_LIKES,
        canContinue = true,
        onContinueClick = {},
        onSkipClick = {}
    )
}
